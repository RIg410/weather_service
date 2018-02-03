import React, {Component} from 'react';
import './App.css';

class App extends Component {
    render() {
        return (
            <div className="Input-div">
                <h1 className="Block-title Unselected">Check the weather!</h1>
                <div>
                    <div className={"Input-variable-text Unselected"}>Use city name.</div>
                    <input className="Text-input" id="CityNameInput" type="text"/>
                    <div className={"Input-variable-text Unselected"}>or geo coordinates.</div>
                    <input className="Text-input" id="LatInput" type="text"/>
                    <input className="Text-input" id="LonInput" type="text"/>
                    <div className={"Button Unselected"}>GO!</div>
                </div>
            </div>
        );
    }
}

export default App;


