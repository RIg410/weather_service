import React, {Component} from 'react';
import './App.css';
import pending from './loading.gif';

class Pending extends Component {
    render() {
        return (
            <div className={`Pending-view ${this.props.show_pending ? "": "Hidden"}`}>
                <img id={"Pending-view-img"} src={pending} alt="pending"/>
                <h1 className={"Pending-text Unselected"}>Wait a second...</h1>
            </div>
        );
    }
}

export default Pending;


