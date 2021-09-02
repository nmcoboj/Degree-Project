from flask import Flask, jsonify, request, Response
from flask_cors import *
from camera_pi import Camera
from importlib import import_module
import os
import mover
import servos
import luces
import ultra
import time
import threading

Luces = luces.LED()
Estado = "Apagar"
R = 255
G = 255
B = 255

app = Flask(__name__)
CORS(app, supports_credentials=True)

# @app.route('/api/echo', methods = ['POST', 'GET'])
# def add():
#     data = request.get_json(force=True)
#     print(data.get('movimiento'))
#     print(data)
#     return jsonify(data)

def gen(camera):
    """Video streaming generator function."""
    while True:
        frame = camera.get_frame()
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

@app.route('/video_feed', methods = ['GET'])
def video_feed():
    """Video streaming route. Put this in the src attribute of an img tag."""
    return Response(gen(Camera()),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/api/motorantebrazo', methods = ['POST'])
def motor_antebrazo():
    data = request.get_json(force=True)
    movimiento = data.get('movimiento')
    respuesta = servos.servo_antebrazo(movimiento)
    return jsonify(movimiento=respuesta)

@app.route('/api/motorbrazo', methods = ['POST'])
def motor_brazo():
    data = request.get_json(force=True)
    movimiento = data.get('movimiento')
    respuesta = servos.servo_brazo(movimiento)
    return jsonify(movimiento=respuesta)

@app.route('/api/motorcamara', methods = ['POST'])
def motor_camara():
    data = request.get_json(force=True)
    movimiento = data.get('movimiento')
    respuesta = servos.servo_camara(movimiento)
    return jsonify(movimiento=respuesta)

@app.route('/api/motormano', methods = ['POST'])
def motor_mano():
    data = request.get_json(force=True)
    movimiento = data.get('movimiento')
    respuesta = servos.servo_mano(movimiento)
    return jsonify(movimiento=respuesta)

@app.route('/api/motorpinza', methods = ['POST'])
def motor_pinza():
    data = request.get_json(force=True)
    movimiento = data.get('movimiento')
    respuesta = servos.servo_pinza(movimiento)
    return jsonify(movimiento=respuesta)

@app.route('/api/motorderecho', methods = ['POST'])
def motor_derecho():
    mensaje=''
    data = request.get_json(force=True)
    movimiento = data.get('movimiento')
    if movimiento == 'Adelante0':
        respuesta = mover.mover_motorderecho(0, 'Adelante')
    elif movimiento == 'Adelante50':
        respuesta  = mover.mover_motorderecho(50, 'Adelante')
    elif movimiento == 'Adelante100':
        respuesta  = mover.mover_motorderecho(100, 'Adelante')
    elif movimiento == 'Atras0':
        respuesta  = mover.mover_motorderecho(0, 'Atras')
    elif movimiento == 'Atras50':
        respuesta  = mover.mover_motorderecho(50, 'Atras')
    elif movimiento == 'Atras100':
        respuesta  = mover.mover_motorderecho(100, 'Atras')
    elif movimiento == 'Izquierda0':
        respuesta  = mover.mover_motorderecho(0, 'Izquierda')
    elif movimiento == 'Izquierda50':
        respuesta  = mover.mover_motorderecho(50, 'Izquierda')
    elif movimiento == 'Izquierda100':
        respuesta  = mover.mover_motorderecho(100, 'Izquierda')        
    elif movimiento == 'Derecha0':
        respuesta  = mover.mover_motorderecho(0, 'Derecha')
    elif movimiento == 'Derecha50':
        respuesta  = mover.mover_motorderecho(50, 'Derecha')
    elif movimiento == 'Derecha100':
        respuesta  = mover.mover_motorderecho(100, 'Derecha')
    contador_derecho()
    return jsonify(movimiento=respuesta)

@app.route('/api/motorizquierdo', methods = ['POST'])
def motor_izquierdo():
    mensaje=''
    data = request.get_json(force=True)
    movimiento = data.get('movimiento')
    if movimiento == 'Adelante0':
        respuesta = mover.mover_motorizquierdo(0, 'Adelante')
    elif movimiento == 'Adelante50':
        respuesta = mover.mover_motorizquierdo(50, 'Adelante')
    elif movimiento == 'Adelante100':
        respuesta = mover.mover_motorizquierdo(100, 'Adelante')
    elif movimiento == 'Atras0':
        respuesta = mover.mover_motorizquierdo(0, 'Atras')
    elif movimiento == 'Atras50':
        respuesta = mover.mover_motorizquierdo(50, 'Atras')
    elif movimiento == 'Atras100':
        respuesta = mover.mover_motorizquierdo(100, 'Atras')
    elif movimiento == 'Izquierda0':
        respuesta = mover.mover_motorizquierdo(0, 'Izquierda')
    elif movimiento == 'Izquierda50':
        respuesta = mover.mover_motorizquierdo(50, 'Izquierda')
    elif movimiento == 'Izquierda100':
        respuesta = mover.mover_motorizquierdo(100, 'Izquierda')        
    elif movimiento == 'Derecha0':
        respuesta = mover.mover_motorizquierdo(0, 'Derecha')
    elif movimiento == 'Derecha50':
        respuesta = mover.mover_motorizquierdo(50, 'Derecha')
    elif movimiento == 'Derecha100':
        respuesta = mover.mover_motorizquierdo(100, 'Derecha')
    contador_izquierdo()
    return jsonify(movimiento=respuesta)

@app.route('/api/luces', methods = ['POST'])
def luces():
    global Luces
    global Estado, R, G, B
    data = request.get_json(force=True)
    accion = data.get('accion')

    if accion == 'Encender':
        Estado = 'Encender'
        Luces.colorWipe(R,G,B)
    elif accion == 'Apagar':
        Estado = 'Apagar'

    if Estado == 'Encender':
        if accion == '#ffffff':
            R = 255
            G = 255
            B = 255
        elif accion == '#ff0000':
            R = 255
            G = 0
            B = 0
        elif accion == '#ffff00':
             R = 255
             G = 255
             B = 0
        elif accion == '#0000ff':
             R = 0
             G = 0
             B = 255
        elif accion == '#00ff00':
             R = 0
             G = 255
             B = 0
        elif accion == '#ff00ff':
             R = 255
             G = 0
             B = 255
        elif accion == '#00ffff':
             R = 0
             G = 255
             B = 255
        Luces.colorWipe(R, G, B)
    else:
        if accion == '#ffffff':
            R = 255
            G = 255
            B = 255
        elif accion == '#ff0000':
            R = 255
            G = 0
            B = 0
        elif accion == '#ffff00':
             R = 255
             G = 255
             B = 0
        elif accion == '#0000ff':
             R = 0
             G = 0
             B = 255
        elif accion == '#00ff00':
             R = 0
             G = 255
             B = 0
        elif accion == '#ff00ff':
             R = 255
             G = 0
             B = 255
        elif accion == '#00ffff':
             R = 0
             G = 255
             B = 255
        Luces.colorWipe(0, 0, 0)

    return jsonify(accion=True)

def inicializar():
    mover.setup()
    servos.setup()
    Luces.colorWipe(0,0,0)

def detener():
    mover.detener()
    Luces.colorWipe(0,0,0)

def contador_izquierdo():
    timer = threading.Timer(1.2, parar_izquierdo)
    timer.start()
    
def parar_izquierdo ():
    mover.motorStopIzquierdo()

def contador_derecho():
    timer = threading.Timer(1.2, parar_derecho)
    timer.start()
    
def parar_derecho():
    mover.motorStopDerecho()



if __name__ == '__main__':
    try:
        inicializar()
        #ngrok
        #app.run(threaded=True)
        app.run(host="0.0.0.0", threaded=True)
    except KeyboardInterrupt:
        detener()
        
# ssl_context='adhoc'
#host="0.0.0.0", 