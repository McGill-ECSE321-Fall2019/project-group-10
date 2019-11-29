import os
import cv2

cam = cv2.VideoCapture(0)

img_counter = 0

while True:
    ret, frame = cam.read()
    cv2.imshow("Capturing", frame)
    if not ret:
        break
        
    img_name = "predictionB.png".format(img_counter)
    cv2.imwrite(img_name, frame)
    img_counter += 1

    break

cam.release()

cv2.destroyAllWindows()

os.system("echo Picture successfully taken...")
os.system("./facialRecog detect cfg/yolov3-tiny.cfg facial_recog.weights predictionB.png | tee prediction.txt")
os.system("./facialRecog detect cfg/yolov3-tiny.cfg facial_recog.weights predictions.jpg | tee prediction.txt")
os.system("./runScriptBash.sh")

