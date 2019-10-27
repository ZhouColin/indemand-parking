import React, { Component } from "react"
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
import Sidebar from "react-sidebar";

const mapStyles = {
  width: '100%',
  height: '100%',
};

export class MapContainer extends Component {
  constructor(props) {
    super(props);

    this.state = {
      stores: [{lat: 47.49855629475769, lng: -122.14184416996333},
              {latitude: 47.359423, longitude: -122.021071},
              {latitude: 47.2052192687988, longitude: -121.988426208496},
              {latitude: 47.6307081, longitude: -122.1434325},
              {latitude: 47.3084488, longitude: -122.2140121},
              {latitude: 47.5524695, longitude: -122.0425407}],
      sidebarOpen: false
    }
    this.onSetSidebarOpen = this.onSetSidebarOpen.bind(this);
  }

  onSetSidebarOpen(open) {
    this.setState({ sidebarOpen: open });
  }

  displayMarkers = () => {
    return this.state.stores.map((store, index) => {
      return <Marker key={index} id={index} position={{
       lat: store.latitude,
       lng: store.longitude
     }}
     onClick={() => this.onSetSidebarOpen(true)} />
    })
  }

  // function displayOnSidebar ({ lat, lng }) {
  //   return <div className='message-box'>
  //     Hello {name}
  //   </div>
  // }

  render() {
    return (
        <Map
          google={this.props.google}
          zoom={8}
          style={mapStyles}
          initialCenter={{ lat: 47.444, lng: -122.176}}
        >
          {this.displayMarkers()}
          <Sidebar
            sidebar={<b>Sidebar content</b>}
            open={this.state.sidebarOpen}
            onSetOpen={this.onSetSidebarOpen}
            styles={{ sidebar: { background: "white" } }}
          >
            <button onClick={() => this.onSetSidebarOpen(true)}>
              Open sidebar
            </button>
          </Sidebar>
        </Map>

    );
  }
}

export default GoogleApiWrapper({
  apiKey: 'AIzaSyB3KpTdPxZeYhdLtyxZSmXxZhowxqVWoyk'
})(MapContainer);
