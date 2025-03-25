from flask import Flask, jsonify, request, send_file
import google.generativeai as genai
import os
app = Flask(__name__)

APIKEY = ""

genai.configure(api_key=APIKEY)

model = genai.GenerativeModel(model_name='gemini-1.5-flash')

@app.route('/api/hello',methods=['GET'])
def hello():
    response = model.generate_content('1+1')
    print(response.text)
    return jsonify({'message:':'Hello World'})

app.run()





