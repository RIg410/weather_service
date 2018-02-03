import React, {Component} from 'react';
import Input from './Input.js';
import logo from './cloud.png';
import './App.css';

class App extends Component {
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
                    <Input/>
                </div>
            </div>
        );
    }
}

export default App;
