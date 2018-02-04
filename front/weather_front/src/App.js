import React, {Component} from 'react';
import Input from './Input.js';
import logo from './cloud.png';
import './App.css';
import Pending from "./Pending";
import Weather from "./Weather";
import Error from "./Error";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isInputEnable: true,
            showPending: false,
            showError: false,
            showWeather: false,
            region: "",
            weather: null,
            error: {
                title: "something wrong :(",
                why: "some error",
            }
        };
    }

    searchByName(name) {
        if (App.isEmpty(name)) {
            this.setState({
                isInputEnable: false,
                showError: true,
                error: {
                    title: "Please. Check yourself.",
                    why: "When you search by city name, field name must not be empty.",
                }
            });
        } else {
            this.setState({
                region: `name`,
                isInputEnable: false,
                showPending: true
            });
            let params = 'q=' + encodeURIComponent(name);
            this.performAsyncWeatherRequest(params);
        }
    }

    searchByGeo(lat, lon) {
        const err = App.checkGeoData(lat, lon);
        if (err) {
            this.setState({
                isInputEnable: false,
                showError: true,
                error: {
                    title: "Please. Check yourself.",
                    why: err,
                }
            });
        } else {
            this.setState({
                region: `(lat:${lat}; lon:${lon})`,
                isInputEnable: false,
                showPending: true
            });
            let params = `lat=${encodeURIComponent(lat)}&lon=${encodeURIComponent(lon)}`;
            this.performAsyncWeatherRequest(params);
        }
    }

    performAsyncWeatherRequest(param) {
        new Promise(function (resolve, reject) {
            try {
                let xhr = new XMLHttpRequest();
                xhr.open('GET', `http://localhost:8080/api/weather?${param}`, false);
                xhr.send();
                if (xhr.status !== 200) {
                    reject("Failed to communicate with the server. Try again later.");
                } else {
                    const resp = JSON.parse(xhr.responseText);
                    if (resp.status === "FINISH") {
                        resolve(resp.result);
                    } else if (resp.status === "ERROR") {
                        reject(resp.err);
                    } else {
                        let timerId = setInterval(() => {
                            let xhr = new XMLHttpRequest();
                            xhr.open('GET', `http://localhost:8080/api/track/${resp.trackId}`, false);
                            xhr.send();
                            if (xhr.status !== 200) {
                                clearInterval(timerId);
                                reject("Failed to communicate with the server. Try again later.");
                            } else {
                                const resp = JSON.parse(xhr.responseText);
                                if (resp.status === "FINISH") {
                                    clearInterval(timerId);
                                    resolve(resp.result);
                                } else if (resp.status === "ERROR") {
                                    clearInterval(timerId);
                                    reject(resp.err);
                                }
                            }
                        }, 200);
                    }
                }
            } catch (err) {
                console.error(err);
                reject("Failed to communicate with the server. Try again later.");
            }
        }).then(
            result => {
                this.setState({
                    isInputEnable: true,
                    showPending: false,
                    showError: false,
                    showWeather: true,
                    weather: result,
                });
            },
            err => {
                this.setState({
                    isInputEnable: false,
                    showPending: false,
                    showError: true,
                    error: {
                        title: "Something wrong!",
                        why: err,
                    }
                });
            }
        );
    }

    closeError() {
        this.setState({
            isInputEnable: true,
            showPending: false,
            showError: false,
        });
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <div className="App-logo">
                        <img src={logo} className="App-logo-img" alt="logo"/>
                        <h1 className={"App-title Unselected"}>Weather service</h1>
                    </div>
                </header>
                <div className="Wrap">
                    <Input isEnable={this.state.isInputEnable}
                           searchByGeo={this.searchByGeo.bind(this)}
                           searchByName={this.searchByName.bind(this)}/>
                    <Pending showPending={this.state.showPending}/>
                    <Error showError={this.state.showError} closeError={this.closeError.bind(this)}
                           error={this.state.error}/>
                    {this.weather()}
                </div>
            </div>
        );
    }

    weather() {
        if (this.state.showWeather) {
            return <Weather showWeather={this.state.showWeather} weather={this.state.weather} region={this.state.region}/>;
        }
        return null;
    }


    static checkGeoData(lat, lon) {
        const err = App.checkGeoField(lat, "lat");
        if (err) {
            return err;
        } else {
            return App.checkGeoField(lon, "lon");
        }
    }

    static checkGeoField(field, name) {
        if (App.isEmpty(field)) {
            return `When you search by geo coordinates, field ${name} must not be empty.`
        }
        if (isNaN(field) || parseFloat(field) < 0) {
            return `When you search by geo coordinates, field ${name} must be a positive number.`
        }
        return null;
    }

    static isEmpty(str) {
        return (!str || 0 === str.length);
    }
}

export default App;
