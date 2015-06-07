import socket
from threading import Thread
from Tkinter import Tk, Label, Entry, Button
from PIL import ImageTk
import qrcode


class Server:
    def __init__(self):
        self.HOST = socket.gethostbyname(socket.gethostname())
        self.PORT = 8000
        self.conn, self.addr = None, None
        self.qrImage = None
        self.socket = None
        self.initConnection()
        self.ChatUi()

    def initConnection(self):
        qr = qrcode.QRCode()
        qr.add_data(self.HOST)
        qr.make()
        self.qrImage = qr.make_image()
        main = Tk()
        photo = ImageTk.PhotoImage(self.qrImage)
        label = Label(image=photo)
        label.pack()
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        def task():
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.socket.bind((self.HOST, self.PORT))
            self.socket.listen(1)
            self.conn, self.addr = self.socket.accept()
            print 'connected by ' + repr(self.addr)
            data = self.conn.recv(1024)
            if "123" in str(data):
                self.conn.sendall("456")
            main.destroy()
        main.after(100, task)
        main.title("Scan this QR")
        main.mainloop()

    def ChatUi(self):
        self.chat = Tk()
        self.chat.title("Chat demo")

        self.chatBox = Label(self.chat, height=20, width=50)
        self.chatBox.pack()

        self.ipBox = Entry(self.chat, width=50)
        self.ipBox.pack()

        self.sendBtn = Button(self.chat, command=self.sendMsg, text="Send", width=50)
        self.sendBtn.pack()

        Thread(target=self.recieveMsg).start()

        self.chat.mainloop()


    def recieveMsg(self):
        while True:
            data = self.conn.recv(1024)
            text = self.chatBox.cget("text") + "\n" + data
            self.chatBox.configure(text=text)


    def sendMsg(self):
        reply = self.ipBox.get()
        text = self.chatBox.cget("text") + "\n" + reply
        self.chatBox.configure(text=text)
        self.conn.sendall(reply)


if __name__ == '__main__':
    s = Server()