import React from 'react';

class SignupForm extends React.Component {
  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  first='';
  last='';
  username='';
  password='';
  phone='';

  handleSubmit(e) {
    e.preventDefault();

    var base = 'http://localhost:8080/signup?';
    var req = base + "username=" + this.username.value
      + '&' + "password=" + this.password.value
      + '&' + "firstName=" + this.first.value
      + '&' + "lastName=" + this.last.value
      + '&' + "phoneNumber=" + this.phone.value;
    alert(req);

    fetch(`http://localhost:8080/signup?username=un&password=pw&firstName=a&lastName=b&phoneNumber=5`
            // method: "GET",
            // headers: {
            //   'Accept': 'application/json',
            //   'Content-Type': 'application/json'
            // },
          ).then(response => {
            return response.text();
          }).then((text) => {
            console.log(text);
          })
  }
  //
  // handleClick(event){
  //   var apiBaseUrl = "http://localhost:4000/api/";
  //   console.log("values",this.state.first_name,this.state.last_name,this.state.email,this.state.password);
  //   //To be done:check for empty values before hitting submit
  //   var self = this;
  //   var payload={
  //   "first_name": this.state.first_name,
  //   "last_name":this.state.last_name,
  //   "email":this.state.email,
  //   "password":this.state.password
  //   }
  //   axios.post(apiBaseUrl+'/register', payload)
  //  .then(function (response) {
  //    console.log(response);
  //    if(response.data.code == 200){
  //     //  console.log("registration successfull");
  //      var loginscreen=[];
  //      loginscreen.push(<Login parentContext={this}/>);
  //      var loginmessage = "Not Registered yet.Go to registration";
  //      self.props.parentContext.setState({loginscreen:loginscreen,
  //      loginmessage:loginmessage,
  //      buttonLabel:"Register",
  //      isLogin:true
  //       });
  //    }
  //  })
  //  .catch(function (error) {
  //    console.log(error);
  //  });

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label> First Name:
          <input type="text" ref={(input) => this.first = input} />
        </label>
        <br></br>
        <label> Last Name:
          <input type="text" ref={(input) => this.last = input} />
        </label>
        <br></br>
        <label> Username:
          <input type="text" ref={(input) => this.username = input} />
        </label>
        <br></br>
        <label> Password:
          <input type="text" ref={(input) => this.password = input} />
        </label>
        <br></br>
        <label> Phone Number:
          <input type="text" ref={(input) => this.phone = input} />
        </label>
        <br></br>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}

export default SignupForm
