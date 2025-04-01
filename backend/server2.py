from fastapi import FastAPI
from fastapi.responses import JSONResponse
import google.generativeai as genai
from dotenv import load_dotenv
import uvicorn
import os

# Initialize FastAPI app
app = FastAPI()

# Load environment variables
load_dotenv()

APIKEY = os.getenv('GEMINI_API_KEY')
genai.configure(api_key=APIKEY)
model = genai.GenerativeModel(model_name='gemini-1.5-flash')

@app.get("/api/hello")
async def hello():
    return JSONResponse(content={'message:': 'Server is running!'})

@app.get("/api/gemini/{question}")
async def gemini_response(question: str):
    response = model.generate_content(question)
    print(response)
    return JSONResponse(content={'message:': response.text})

uvicorn.run(app, host="0.0.0.0", port=5001)
