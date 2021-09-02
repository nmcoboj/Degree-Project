import ultra
import time
import RPi.GPIO as GPIO

Motor_Izquierdo   = 17
Motor_Derecho    = 4

Motor_Izquierdo_Pin1  = 27
Motor_Izquierdo_Pin2  = 18
Motor_Derecho_Pin1  = 14
Motor_Derecho_Pin2  = 15

Dir_forward   = 0
Dir_backward  = 1

Izquierdo_adelante  = 0
Izquierdo_atras = 1

Derecho_adelante = 0
Derecho_atras= 1

pwm_Izquierdo = 0
pwm_Derecho = 0

def motorStop():#Motor stops
    GPIO.output(Motor_Izquierdo_Pin1, GPIO.LOW)
    GPIO.output(Motor_Izquierdo_Pin2, GPIO.LOW)
    GPIO.output(Motor_Derecho_Pin1, GPIO.LOW)
    GPIO.output(Motor_Derecho_Pin2, GPIO.LOW)
    GPIO.output(Motor_Izquierdo, GPIO.LOW)
    GPIO.output(Motor_Derecho, GPIO.LOW)

def motorStopIzquierdo():#Motor stops
    GPIO.output(Motor_Izquierdo_Pin1, GPIO.LOW)
    GPIO.output(Motor_Izquierdo_Pin2, GPIO.LOW)
    GPIO.output(Motor_Izquierdo, GPIO.LOW)

def motorStopDerecho():#Motor stops
    GPIO.output(Motor_Derecho_Pin1, GPIO.LOW)
    GPIO.output(Motor_Derecho_Pin2, GPIO.LOW)
    GPIO.output(Motor_Derecho, GPIO.LOW)

def setup():#Motor initialization
    global pwm_Izquierdo, pwm_Derecho
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(Motor_Izquierdo, GPIO.OUT)
    GPIO.setup(Motor_Derecho, GPIO.OUT)
    GPIO.setup(Motor_Izquierdo_Pin1, GPIO.OUT)
    GPIO.setup(Motor_Izquierdo_Pin2, GPIO.OUT)
    GPIO.setup(Motor_Derecho_Pin1, GPIO.OUT)
    GPIO.setup(Motor_Derecho_Pin2, GPIO.OUT)
    motorStop()
    try:
        pwm_Izquierdo = GPIO.PWM(Motor_Izquierdo, 1000)
        pwm_Derecho = GPIO.PWM(Motor_Derecho, 1000)
    except:
        pass


def motor_right(movimiento, speed):#Motor 2 positive and negative rotation
    if movimiento == Dir_backward:
        GPIO.output(Motor_Derecho_Pin1, GPIO.HIGH)
        GPIO.output(Motor_Derecho_Pin2, GPIO.LOW)
        pwm_Derecho.start(100)
        pwm_Derecho.ChangeDutyCycle(speed)
    elif movimiento == Dir_forward:
        GPIO.output(Motor_Derecho_Pin1, GPIO.LOW)
        GPIO.output(Motor_Derecho_Pin2, GPIO.HIGH)
        pwm_Derecho.start(0)
        pwm_Derecho.ChangeDutyCycle(speed)


def motor_left(movimiento, speed):#Motor 1 positive and negative rotation
    if movimiento == Dir_forward:
        GPIO.output(Motor_Izquierdo_Pin1, GPIO.HIGH)
        GPIO.output(Motor_Izquierdo_Pin2, GPIO.LOW)
        pwm_Izquierdo.start(100)
        pwm_Izquierdo.ChangeDutyCycle(speed)
    elif movimiento == Dir_backward:
        GPIO.output(Motor_Izquierdo_Pin1, GPIO.LOW)
        GPIO.output(Motor_Izquierdo_Pin2, GPIO.HIGH)
        pwm_Izquierdo.start(0)
        pwm_Izquierdo.ChangeDutyCycle(speed)

def mover_motorizquierdo(speed, movimiento):
    if movimiento == 'Adelante':
        distancia = ultra.checkdist()
        if distancia <= 0.3:
            respuesta = 'Obstaculo'
        else:
            motor_left(Izquierdo_adelante, speed)
            respuesta = 'true'
    elif movimiento == 'Atras':
        motor_left(Izquierdo_atras, speed)
        respuesta = 'true'
    elif movimiento == 'Izquierda':
        motor_left(Izquierdo_atras, speed)
        respuesta = 'true'
    elif movimiento == 'Derecha':
        motor_left(Izquierdo_adelante, speed)
        respuesta = 'true'
    else:
        motorStop()
    return respuesta

def mover_motorderecho(speed, movimiento):
    if movimiento == 'Adelante':
        distancia = ultra.checkdist()
        if distancia <= 0.3:
            respuesta = 'Obstaculo'
        else:
            motor_right(Derecho_adelante, speed)
            respuesta = 'true'
    elif movimiento == 'Atras':
        motor_right(Derecho_atras, speed)
        respuesta = 'true'
    elif movimiento == 'Izquierda':
        motor_right(Derecho_adelante, speed)
        respuesta = 'true'
    elif movimiento == 'Derecha':
        motor_right(Derecho_atras, speed)
        respuesta = 'true'
    else:
        motorStop()
    return respuesta

def detener():
    motorStop()
    GPIO.cleanup()             # Release resource

if __name__ == '__main__':
    try:
        speed_set = 50
        setup()
        mover_motorizquierdo(speed_set, 'Adelante')
        mover_motorderecho(speed_set, 'Adelante')
        time.sleep(3)
        detener()
    except KeyboardInterrupt:
        destroy()
