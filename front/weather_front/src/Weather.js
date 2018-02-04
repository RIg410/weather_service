import React, {Component} from 'react';
import './App.css';

class Weather extends Component {
    render() {
        let region = Weather.isEmpty(this.props.weather.name) ? this.props.region : this.props.weather.name;
        return (
            <div className={`Weather-view  ${this.props.showWeather ? "" : "Hidden"}`}>
                <h1>Current weather in {region}</h1>
            </div>
        );
    }

    static isEmpty(str) {
        return (!str || 0 === str.length);
    }
}

export default Weather;


