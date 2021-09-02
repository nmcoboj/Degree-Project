import Adafruit_PCA9685
import time
pwm = Adafruit_PCA9685.PCA9685()
pwm.set_pwm_freq(50)

angulo_antebrazo = 300
angulo_brazo = 300
angulo_camara = 450
angulo_mano = 300
angulo_pinza = 290

motor_antebrazo=13
motor_brazo=12
motor_camara=11
motor_mano=14
motor_pinza = 15

def servo_antebrazo(movimiento):
    global angulo_antebrazo
    global motor_antebrazo
    if movimiento == 'Bajar Antebrazo':
        if angulo_antebrazo == 200:
            angulo_antebrazo = 300
            pwm.set_pwm(motor_antebrazo, 0, angulo_antebrazo)
            respuesta = 'true'
        elif angulo_antebrazo == 300:
            angulo_antebrazo = 400
            pwm.set_pwm(motor_antebrazo, 0, angulo_antebrazo)
            respuesta = 'true'
        elif angulo_antebrazo == 400:
            respuesta = 'full'
    else:
        if angulo_antebrazo == 200:
            respuesta = 'full'
        elif angulo_antebrazo == 300:
            angulo_antebrazo = 200
            pwm.set_pwm(motor_antebrazo, 0, angulo_antebrazo)
            respuesta = 'true'
        elif angulo_antebrazo == 400:
            angulo_antebrazo = 300
            pwm.set_pwm(motor_antebrazo, 0, angulo_antebrazo)
            respuesta = 'true'
    return respuesta

def servo_brazo(movimiento):
    global angulo_brazo
    global motor_brazo
    if movimiento == 'Subir Brazo':
        if angulo_brazo == 200:
            angulo_brazo = 300
            pwm.set_pwm(motor_brazo, 0, angulo_brazo)
            respuesta = 'true'
        elif angulo_brazo == 300:
            angulo_brazo = 400
            pwm.set_pwm(motor_brazo, 0, angulo_brazo)
            respuesta = 'true'
        elif angulo_brazo == 400:
            respuesta = 'full'
    else:
        if angulo_brazo == 200:
            respuesta = 'full'
        elif angulo_brazo == 300:
            angulo_brazo = 200
            pwm.set_pwm(motor_brazo, 0, angulo_brazo)
            respuesta = 'true'
        elif angulo_brazo == 400:
            angulo_brazo = 300
            pwm.set_pwm(motor_brazo, 0, angulo_brazo)
            respuesta = 'true'
    return respuesta

def servo_camara(movimiento):
    global angulo_camara
    global motor_camara
    if movimiento == 'Subir Camara':
        if angulo_camara == 450:
            angulo_camara = 400
            pwm.set_pwm(motor_camara, 0, angulo_camara)
            respuesta = 'true'
        elif angulo_camara == 400:
            angulo_camara = 350
            pwm.set_pwm(motor_camara, 0, angulo_camara)
            respuesta = 'true'
        elif angulo_camara == 350:
            angulo_camara = 300
            pwm.set_pwm(motor_camara, 0, angulo_camara)
            respuesta = 'true'
        elif angulo_camara == 300:
            respuesta = 'full'
    else:
        if angulo_camara == 450:
            respuesta = 'full'
        elif angulo_camara == 300:
            angulo_camara = 350
            pwm.set_pwm(motor_camara, 0, angulo_camara)
            respuesta = 'true'
        elif angulo_camara == 350:
            angulo_camara = 400
            pwm.set_pwm(motor_camara, 0, angulo_camara)
            respuesta = 'true'
        elif angulo_camara == 400:
            angulo_camara = 450
            pwm.set_pwm(motor_camara, 0, angulo_camara)
            respuesta = 'true'
    return respuesta

def servo_mano(movimiento):
    global angulo_mano
    global motor_mano
    if movimiento == 'Mano a la Derecha':
        if angulo_mano == 100:
            angulo_mano = 300
            pwm.set_pwm(motor_mano, 0, angulo_mano)
            respuesta = 'true'
        elif angulo_mano == 300:
            angulo_mano = 500
            pwm.set_pwm(motor_mano, 0, angulo_mano)
            respuesta = 'true'
        elif angulo_mano == 500:
            respuesta = 'full'
    else:
        if angulo_mano == 100:
            respuesta = 'full'
        elif angulo_mano == 300:
            angulo_mano = 100
            pwm.set_pwm(motor_mano, 0, angulo_mano)
            respuesta = 'true'
        elif angulo_mano == 500:
            angulo_mano = 300
            pwm.set_pwm(motor_mano, 0, angulo_mano)
            respuesta = 'true'
    return respuesta

def servo_pinza(movimiento):
    global angulo_pinza
    global motor_pinza
    if movimiento == 'Abrir Pinza':
        if angulo_pinza == 290:
            angulo_pinza = 110
            pwm.set_pwm(motor_pinza, 0, angulo_pinza)
            respuesta = 'true'
        else:
            respuesta = 'full'
    else:
        if angulo_pinza == 110:
            angulo_pinza = 290
            pwm.set_pwm(motor_pinza, 0, angulo_pinza)
            respuesta = 'true'
        else:
            respuesta = 'full'
    return respuesta

def setup():
    pwm.set_pwm(motor_antebrazo, 0, angulo_antebrazo)
    pwm.set_pwm(motor_brazo, 0, angulo_brazo)
    pwm.set_pwm(motor_camara, 0, angulo_camara)
    pwm.set_pwm(motor_pinza, 0, angulo_pinza)
    pwm.set_pwm(motor_mano, 0, angulo_mano)

    