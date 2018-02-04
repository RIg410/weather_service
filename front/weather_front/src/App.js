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
            is_input_enable: false,
            show_pending: false,
            show_error: true,
            show_weather: true,
            region: "London",
            error: {
                title:"something wrong :(",
                why:"some error",
            }
        };
    }

    static searchByName(name) {
        this.setState({
            is_input_enable: false,
            show_pending: true
        });
    }

    static searchGao(lat, lon) {
        this.setState({
            is_input_enable: false,
            show_pending: true
        });
    }

    closeError() {
        this.setState({
            is_input_enable :true,
            show_pending: false,
            show_error: false,
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
                    <Input isEnable={this.state.is_input_enable}
                           searchByGeo={App.searchGao}
                           searchByName={App.searchByName.bind(this)}
                           show_pending={this.state.show_pending}
                    />
                    <Pending show_pending={this.state.show_pending}/>
                    <Error show_error={this.state.show_error} closeError={this.closeError.bind(this)}
                    error={this.state.error}/>
                    <Weather show_weather={this.state.show_weather}
                             region={this.state.region}/>
                </div>
            </div>
        );
    }
}

export default App;
