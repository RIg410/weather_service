import React, {Component} from 'react';
import './App.css';

class Weather extends Component {
    render() {
        let weather = this.props.weather.main;
        let wind = this.props.weather.wind;
        let region = Weather.isEmpty(this.props.weather.name) ? this.props.region : this.props.weather.name;
        return (
            <div id={"Weather-view"} className={`${this.props.showWeather ? "" : "Hidden"}`}>
                <h1 id={"Weather-title"} className={"Unselected"}>
                    Current weather in {region}.
                </h1>
                <div>
                    <div id={"temp-div"} className={"Telemetry-wrap"}>
                        <div>
                            <p className={"Telemetry-title"}>Temperature</p>
                            <div className={"Telemetry-block"}>
                                <div className={"tab"}>
                                    <span>current: </span>
                                    <span className={"Telemetry-val"}>{weather.temp}</span>
                                </div>
                                <div className={"tab"}>
                                    <span>maximum: </span>
                                    <span className={"Telemetry-val"}>{weather.temp_max}</span>
                                </div>
                                <div className={"tab"}>
                                    <span>minimum: </span>
                                    <span className={"Telemetry-val"}>{weather.temp_min}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id={"wind-div"} className={"Telemetry-wrap"}>
                        <p className={"Telemetry-title"}>Wind</p>
                        <div className={"Telemetry-block"}>
                            <div className={"tab"}>
                                <span>speed: </span>
                                <span className={"Telemetry-val"}>{wind.speed}</span></div>
                            <div className={"tab"}>
                                <span>deg: </span>
                                <span className={"Telemetry-val"}>{wind.deg}</span></div>
                        </div>
                    </div>
                    <div id={"temp-div"} className={"Telemetry-wrap"}>
                        <p className={"Telemetry-title"}>Common</p>
                        <div className={"Telemetry-block"}>
                            <div className={"tab"}>
                                <span>pressure: </span>
                                <span className={"Telemetry-val"}>{weather.pressure}</span>
                            </div>
                            <div className={"tab"}>
                                <span>humidity: </span>
                                <span className={"Telemetry-val"}>{weather.humidity}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    static isEmpty(str) {
        return (!str || 0 === str.length);
    }
}

export default Weather;


/*"wind\":{\"speed\":13.26,\"deg\":274.0}, main\":{\"temp\":280.152,\"pressure\":1014.3,\"humidity\":100.0,\"temp_min\":280.152,\"temp_max\":280.152},\"name\":\"Shuzenji\"}"),*/
