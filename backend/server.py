from flask import Flask, jsonify, request, send_file
import google.generativeai as genai
from dotenv import load_dotenv
import os
app = Flask(__name__)

load_dotenv()

APIKEY = os.getenv('GEMINI_API_KEY')

genai.configure(api_key=APIKEY)

model = genai.GenerativeModel(model_name='gemini-1.5-flash')

@app.route('/api/question/<question>',methods=['GET'])
def hello(question):
    response = model.generate_content(str(question))
    return jsonify({'message:':response.text})

app.run()





