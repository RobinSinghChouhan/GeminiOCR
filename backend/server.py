from flask import Flask, jsonify
import google.generativeai as genai
from dotenv import load_dotenv
import os
app = Flask(__name__)

load_dotenv()

APIKEY = os.getenv('GEMINI_API_KEY')

genai.configure(api_key=APIKEY)

model = genai.GenerativeModel(model_name='gemini-1.5-flash')

@app.route('/',methods=['GET'])
def hello():
    return jsonify({'message:':'Server is runnung!'})

@app.route('/api/gemini/<question>',methods=['GET'])
def geminiResponse(question):
    response = model.generate_content(str(question))
    print(response)
    return jsonify({'message:':response.text})

app.run(host='0.0.0.0', port=5001)