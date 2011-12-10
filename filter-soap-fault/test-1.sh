#!/bin/bash
time echo | curl -v -X POST -d @- -H "Content-Type:text/xml" http://localhost:8080/localhost:18081/echo <<EOS
<soap:Envelope xmlns:soap='http://www.w3.org/2003/05/soap-envelope' xmlns:ako='http://ako.org/echo'>
  <soap:Header />
  <soap:Body>
    <ako:echo>
      <arg0>HelloWorld!</arg0>
    </ako:echo>
  </soap:Body>
</soap:Envelope>
EOS
