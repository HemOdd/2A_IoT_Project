import threading

class KeyboardThread(threading.Thread):

    def __init__(self, input_cbk = None, name='keyboard-input-thread'):
        """
        Intialization
        :param input_cbk:
        :param name:
        """
        self.input_cbk = input_cbk
        super(KeyboardThread, self).__init__(name=name)
        self.start()

    def run(self):
        """
        main function of the program get input from console
        :return: None
        """
        while True:
            result = self.input_cbk(input("Enter a message:\n")) #waits to get input + Return
            if result == -1:
                break
