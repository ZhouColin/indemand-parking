echo "Incorrect login test"
curl "http://localhost:8080/login?username=un&password=pw"
echo
echo

echo "Signup twice with (un, pw), then login login test"
curl "http://localhost:8080/signup?username=un&password=pw"
echo
curl "http://localhost:8080/signup?username=un&password=pw"
echo
curl "http://localhost:8080/login?username=un&password=pw"
echo
echo

{
  curl "http://localhost:8080/signup?username=un2&password=pw2"
  curl "http://localhost:8080/signup?username=un3&password=pw3"
} &> /dev/null

id1=0e9d1a0b-2dee-390b-a773-d527621f6524
id2=e7133154-b862-3977-b4a2-14370855031c
id3=d000c8b2-0e63-308f-a967-97e43739b107

echo "User listing a spot twice should error"
pid1=$(curl "http://localhost:8080/listSpot?uID=${id1}&lon=5&lat=5&time=0&duration=10&meterRate=20")
curl "http://localhost:8080/listSpot?uID=${id1}&lon=5&lat=5&time=0&duration=10&meterRate=20"
echo "(unshown) list two more spots"
pid2=$(curl "http://localhost:8080/listSpot?uID=${id2}&lon=10&lat=10&time=0&duration=10&meterRate=10")
pid3=$(curl "http://localhost:8080/listSpot?uID=${id3}&lon=25&lat=25&time=15&duration=20&meterRate=4")
echo

echo "Test parking spot listing (closest, meterRate, price)"
curl "http://localhost:8080/view?lon=0&lat=0&start=0&end=6&radius=2000000&sortMethod=closest"
echo
curl "http://localhost:8080/view?lon=0&lat=0&start=14&end=16&radius=20000000&sortMethod=meterRate"
echo
curl "http://localhost:8080/view?lon=25&lat=25&start=90&end=100&radius=200000000&sortMethod=price"
echo

echo "Reserve and check no longer available"
curl "http://localhost:8080/reserve?uID=${id1}&psID=${pid1}"
echo
curl "http://localhost:8080/view?lon=0&lat=0&start=0&end=6&radius=2000000&sortMethod=closest"
