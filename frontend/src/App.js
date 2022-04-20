import React, {Component, useState, useEffect} from 'react';
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import logo from './logo.svg';
import './App.css';
import CommitCount from "./CommitCount";

const mapValuesToOptions = (values) => values.map(e => {
        return {'value': e, 'label': e}
    }
);

function App() {
    const [message, setMessage] = useState([]);
    const [committers, setCommitters] = useState({});

    useEffect(() => {
        fetch('/api/hello')
            .then(response => response.text())
            .then(message => {
                setMessage(mapValuesToOptions(JSON.parse(message)));
            });
    }, [])

    const onSelect = (value) => {
        fetchRepoCommits(value.value);
    }

    const fetchRepoCommits = (repo) => {
        if (repo !== "") {
            fetch('/api/commits?repo=' + repo)
                .then(response => response.text())
                .then(message => {
                    if (message !== "") {
                        setCommitters(JSON.parse(message));
                        console.log(JSON.parse(message));
                    } else {
                        setCommitters({})
                    }

                });
        }
    }

    return (
        <div className="App">
            <Dropdown onChange={onSelect} options={message}/>

            <CommitCount commiters={committers}/>
        </div>
    )
}

export default App;
