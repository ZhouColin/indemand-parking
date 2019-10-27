import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect,
  useHistory,
  useLocation
} from "react-router-dom";
import LoginScreen from './loginscreen/LoginScreen';
import MapScreen from './mapscreen/MapScreen';
import SignupScreen from './signupscreen/SignupScreen';


export default function AuthExample() {
  return (
    <Router>
      <div>
        <ul>
          <li>
            <Link to="/signin">Sign In</Link>
          </li>
          <li>
            <Link to="/signup">Sign Up</Link>
          </li>
          <li>
            <Link to="/maps">Maps</Link>
          </li>
        </ul>

        <Switch>
          <Route path="/signup">
            <SignupScreen />
          </Route>
          <Route path="/signin">
            <LoginScreen />
          </Route>
          <Route path="/maps">
            <MapScreen />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

function ProtectedPage() {
  return <h3>Protected</h3>;
}
