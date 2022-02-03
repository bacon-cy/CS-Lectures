import qrcode
userInput = input()
imgName = input()
img = qrcode.make(userInput)
qr = qrcode.QRCode(
    version=5,
    error_correction=qrcode.constants.ERROR_CORRECT_H,
    box_size=8,
    border=4
)
img.save(imgName + ".png")