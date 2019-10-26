echo 'user creation & wrong password test'
curl 'http://localhost:8080/login?username=un&password=pw'
echo
curl 'http://localhost:8080/login?username=un&password=wrong'
echo