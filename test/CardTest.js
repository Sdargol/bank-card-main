const http = require('http');
const options = require('./OptionsCard.js');

const t = [
    {"number":1234123412341234,"status":false,"id":1},
    {"number":1234123412341235,"status":true,"id":2},
    {"number":1234123412341236,"status":true,"id":3}
];

http.get(options.getCards, (res) => {
    let body = '';
    res.on('data', (chunk) => {
      body += chunk;
    });
    res.on('end', () => {
      //console.log(body);

      let b = JSON.parse(body);
      let result = JSON.stringify(b) === JSON.stringify(t);

      console.log("test GetAll /api/v1/cards " + result);
    });
  }).on('error', (e) => {
    console.log("Error: " + e.message);
  }); 


/*const req = http.request(options, res => {
  console.log(`statusCode: ${res.statusCode}`)

  res.on('data', d => {
    //process.stdout.write(d)

    console.log(d);
  })
})

req.on('error', error => {
  console.error(error)
})

console.log(res);

req.end() */