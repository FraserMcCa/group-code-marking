import React, {Component, useState, useEffect} from 'react';
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import logo from './logo.svg';
import './App.css';
const mapValuesToOptions = (values) => values.map(e =>  {
    return { 'value': e, 'label': e}
}
);
function App () {
    const [message, setMessage] = useState([]);

    useEffect(() => {
        fetch('/api/hello')
            .then(response => response.text())
            .then(message => {
                console.log(mapValuesToOptions(JSON.parse(message)))
                setMessage(mapValuesToOptions(JSON.parse(message)));
            });
    },[])
    return (
        <div className="App">
        <header className="App-header">
        <img src={logo} className="App-logo" alt="logo"/>
        <h1 className="App-title">Dog</h1>
        </header>
        <p className="App-intro">To get started, edit <code>src/App.js</code> and save to reload.</p>
        <Dropdown options={message}/>
    </div>
)
}

export default App;
