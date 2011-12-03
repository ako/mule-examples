/* Simple http mock
 */
result = """
<env:Envelope xmlns:env="http://schemas.xmlsoap.org/soap/envelope/">
	<env:Header />
	<env:Body>
		<ns2:EchoResponse xmlns:ns2="http://org.ako/echo">
			<ns2:Message>Hi</ns2:Message>
                </ns2:EchoResponse>
        </env:Body>
</env:Envelope>
"""
return result