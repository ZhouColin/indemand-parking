# Create Users
/usr/bin/curl 'http://localhost:8080/signup?username=a&password=123&firstName=David&lastName=Bian&phoneNumber=+11134567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=b&password=123&firstName=Saurav&lastName=Clmaosrrydude&phoneNumber=+11134567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=c&password=123&firstName=Justin&lastName=DaBaby&phoneNumber=+11134567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=d&password=123&firstName=Colin&lastName=Zhou&phoneNumber=+11134567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=j&password=123&firstName=Roy&lastName=Marth&phoneNumber=+11134567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=i&password=123&firstName=Kite&lastName=Kat&phoneNumber=+11134567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=h&password=123&firstName=La&lastName=DashA&phoneNumber=+11134567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=g&password=123&firstName=John&lastName=Denero&phoneNumber=+11234567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=f&password=123&firstName=Joe&lastName=Scott&phoneNumber=+11224567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=e&password=123&firstName=Alice&lastName=Bob&phoneNumber=+11134567896'
echo

# Create Parking Spot Listings
/usr/bin/curl 'http://localhost:8080/listSpot?uID=80c9ef0f-b863-39cd-a5f9-0af27ef53a9e&lon=-122.254849&lat=37.867425&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=1692fcff-f3e0-3e7b-a8cf-fc2baadef5f5&lon=-122.254767&lat=37.866758&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=94f3b3a1-6d8c-3064-8808-b16bee5003c5&lon=-122.255021&lat=37.867101&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=7097c422-d46b-361f-84c1-69dbbae1c1e6&lon=-122.252198&lat=37.869753&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=3b29ba53-c507-300a-b45c-a7e2cbfd6acf&lon=-122.252948&lat=37.875821&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=dcae1f1d-5c4b-3926-a3da-25c42c000562&lon=-122.255686&lat=37.875961&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=dcae1f1d-5c4b-3926-a3da-25c42c000562&lon=-122.270314&lat=37.867607&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=dcae1f1d-5c4b-3926-a3da-25c42c000562&lon=-122.260555&lat=37.864599&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=dcae1f1d-5c4b-3926-a3da-25c42c000562&lon=-122.273762&lat=37.868953&time=45&duration=10&meterRate=9'
echo

/usr/bin/curl 'http://localhost:8080/parkingClusters?lon=-122.260755&lat=37.869775&radius=50'