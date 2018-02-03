import React, {Component} from 'react';
import './App.css';

class Input extends Component {
    constructor(props) {
        super(props);
        this.state = {
            is_search_by_name: true
        };
    }

    getNameInputClass() {
        return this.state.is_search_by_name ? "Input-on" : "Input-off";
    }

    getGeoInputClass() {
        return !this.state.is_search_by_name ? "Input-on" : "Input-off";
    }

    render() {
        return (
            <div className="Input-div">
                <h1 className="Block-title Unselected">Check the weather!</h1>
                <div>
                    <div className="Input-wrap">
                        <label className={"Input-variable-text Unselected"}>Use city name.</label>
                        <input className={`Text-input ${this.getNameInputClass()}`} id="CityNameInput"
                               onClick={this.selectNameImport.bind(this)} type="text"
                               placeholder="London"/>
                    </div>
                    <div className="Input-wrap">
                        <label className={"Input-variable-text Unselected"}>or geo coordinates.</label>
                        <div>
                            <table id="GeoTable">
                                <tbody>
                                <td>
                                    <tr className="Geo-title">
                                        <div className="Unselected">Lat.</div>
                                    </tr>
                                    <tr>
                                        <input className={`Text-input ${this.getGeoInputClass()}`} id="LatInput"
                                               type="text" placeholder="35" onClick={this.selectGeoImport.bind(this)}/>
                                    </tr>
                                </td>
                                <td>
                                    <tr className="Geo-title">
                                        <div className="Unselected">Lon.</div>
                                    </tr>
                                    <tr>
                                        <input className={`Text-input ${this.getGeoInputClass()}`} id="LonInput"
                                               type="text" placeholder="139" onClick={this.selectGeoImport.bind(this)}/>
                                    </tr>
                                </td>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div className={"Button Unselected"}>GO!</div>
            </div>
        );
    }

    selectNameImport() {
        this.setState({is_search_by_name: true});
    }

    selectGeoImport() {
        this.setState({is_search_by_name: false});
    }
}

export default Input;


