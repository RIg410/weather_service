import React, {Component} from 'react';
import './App.css';

class Weather extends Component {
    render() {
        return (
            <div className={`Weather-view  ${this.props.show_weather ? "": "Hidden"}`}>
               <h1>Current weather in {this.props.region}</h1>
            </div>
        );
    }
}

export default Weather;


