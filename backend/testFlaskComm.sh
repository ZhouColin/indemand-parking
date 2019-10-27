echo 'tests how data is sent between Spring and Flask'
/usr/bin/curl 'http://localhost:8080/signup?username=abc&password=123&firstName=John&lastName=Denero&phoneNumber=+11234567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=abcd&password=123&firstName=Joe&lastName=Scott&phoneNumber=+11224567896'
echo
/usr/bin/curl 'http://localhost:8080/signup?username=abcde&password=123&firstName=Alice&lastName=Bob&phoneNumber=+11134567896'
echo

# List Parking Spots
/usr/bin/curl 'http://localhost:8080/listSpot?uID=e99a18c4-28cb-38d5-b260-853678922e03&lon=1.5&lat=2.5&time=45&duration=10&meterRate=9'
echo
/usr/bin/curl 'http://localhost:8080/listSpot?uID=79cfeb94-595d-333b-b326-c06ab1c7dbda&lon=2&lat=2.5&time=45&duration=10&meterRate=9'
echo

# View Parking Spots
/usr/bin/curl 'http://localhost:8080/view?lon=1.5&lat=2&start=50&end=75&radius=5&sortMethod=closest'
echo
/usr/bin/curl 'http://localhost:8080/view?lon=3&lat=2.5&start=40&end=100&radius=5&sortMethod=closest'
echo
/usr/bin/curl 'http://localhost:8080/parkingClusters?lon=1.5&lat=2.5&radius=10'


