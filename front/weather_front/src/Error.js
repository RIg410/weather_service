import React, {Component} from 'react';
import './App.css';

class Error extends Component {
    render() {
        return (
            <div className={`Error-view  ${this.props.show_error ? "": "Hidden"}`}>
                <p id={"Error-title"}>{this.props.error.title}</p>
                <p id={"Error-text"}>{this.props.error.why}</p>
                <button className={"Button"} onClick={this.closeError.bind(this)}>Ok!</button>
            </div>
        );
    }
    closeError() {
        this.props.closeError();
    }
}

export default Error;


