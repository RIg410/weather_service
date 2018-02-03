import React, {Component} from 'react';
import './App.css';

class App extends Component {
    render() {
        return (
            <div className="Input-div">
                <h1 className="Block-title Unselected">Check the weather!</h1>
                <div>
                    <div className="Input-wrap">
                        <label className={"Input-variable-text Unselected"}>Use city name.</label>
                        <input className="Text-input" id="CityNameInput" type="text" placeholder="London"/>
                    </div>
                    <div className="Input-wrap">
                        <label className={"Input-variable-text Unselected"}>or geo coordinates.</label>
                        <div>
                            <div>
                                <label className="Unselected">Lat.</label>
                                <input className="Text-input" id="LatInput" type="text" placeholder="35"/>
                            </div>
                            <div>
                                <label className="Unselected">Lon.</label>
                                <input className="Text-input" id="LonInput" type="text" placeholder="139"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={"Button Unselected"}>GO!</div>
            </div>
        );
    }
}

export default App;


