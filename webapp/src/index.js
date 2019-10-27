import React from 'react';
import ReactDOM from 'react-dom';
import {Route, Switch, BrowserRouter} from 'react-router-dom'
import './styles/index.css';

import App from './App';
import View404 from './components/View404'
// import * as serviceWorker from './serviceWorker';

const routing = (
  <BrowserRouter>
    <Switch>
      <Route exact={true} path="/" component={App} />
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
