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
            <div className={`Input-div ${this.props.isEnable ? "" : "Input-div-blur"}`}>
                <h1 className="Block-title Unselected">Check the weather!</h1>
                <div>
                    <div className="Input-wrap">
                        <label className={"Input-variable-text Unselected"}>Use city name.</label>
                        <div>
                            <input className={`Text-input ${this.getNameInputClass()}`} id="CityNameInput"
                                   onClick={this.selectNameImport.bind(this)} type="text" ref="cityNameInput"
                                   placeholder="London" disabled={!this.props.isEnable}/>
                        </div>
                    </div>
                    <div className="Input-wrap">
                        <label className={"Input-variable-text Unselected"}>or geo coordinates.</label>
                        <div>
                            <table id="GeoTable">
                                <tbody>
                                <td>
                                    <tr className="Geo-title">
                                        <div className="Unselected">Lat</div>
                                    </tr>
                                    <tr>
                                        <input className={`Text-input ${this.getGeoInputClass()}`} id="LatInput"
                                               ref="latInput"
                                               type="text" placeholder="35"
                                               onClick={this.selectGeoImport.bind(this)}
                                               disabled={!this.props.isEnable}/>
                                    </tr>
                                </td>
                                <td>
                                    <tr className="Geo-title">
                                        <div className="Unselected">Lon</div>
                                    </tr>
                                    <tr>
                                        <input className={`Text-input ${this.getGeoInputClass()}`} id="LonInput"
                                               ref="lonInput"
                                               type="text" placeholder="139"
                                               onClick={this.selectGeoImport.bind(this)}
                                               disabled={!this.props.isEnable}/>
                                    </tr>
                                </td>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <button className={`${this.props.isEnable ? "Button" : "Disabled-button"} Unselected`}
                        disabled={!this.props.isEnable} onClick={this.checkWeather.bind(this)}>GO!
                </button>
            </div>
        );
    }

    checkWeather() {
        if (this.state.is_search_by_name) {
            const name = this.refs.cityNameInput.value;
            this.props.searchByName(name);
        } else {
            const lat = this.refs.latInput.value;
            const lon = this.refs.lonInput.value;
            this.props.searchByGeo(lat, lon);
        }
    }

    selectNameImport() {
        this.setState({is_search_by_name: true});
    }

    selectGeoImport() {
        this.setState({is_search_by_name: false});
    }
}

export default Input;


