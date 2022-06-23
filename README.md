 # NACE Api Service
 
1.PUT - putNaceDetails
 URL : /nace-api/add

Request Body:
{
"orderId": int,
"level": int,
"code": "string",
"parent": "string",
"description": "string",
"includes": "string",
"also_includes": "string",
"rulings": "string",
"excludes": "string",
"referenceISIC_Rev4": "string"
}

2. GET - getNaceDetails
 URL : /nace-api/{orderId}/get

Order id in path variable format

3. PUT - processNaceCSVFile
 URL - /nace-api/csv/upload
 Parameters : CSV File
