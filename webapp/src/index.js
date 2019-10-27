import React from 'react';
import ReactDOM from 'react-dom';
import {Route, Switch, BrowserRouter} from 'react-router-dom'
import './styles/index.css';

import SignupForm from './components/SignupForm'

import App from './App';
import View404 from './components/View404'
import SignupScreen from './signupscreen/SignupScreen'
// import * as serviceWorker from './serviceWorker';

const routing = (
  <BrowserRouter>
    <Switch>
      <Route exact={true} path="/" component={SignupScreen} />
      <Route path="/signup" component={SignupForm} />
      <Route path="/login" component={App} />
      <Route component={View404} />
    </Switch>
  </BrowserRouter>
)
ReactDOM.render(routing, document.getElementById('root'))

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
// serviceWorker.unregister();
