import sounddevice as sd
import soundfile as sf
import numpy as np
import matplotlib.pyplot as plt
import scipy.fftpack
import librosa


#1 SYGNAŁ AUDIO

#2
def sygnalAudio():
    s, fs = sf.read('student.wav', dtype='float32')
    # sd.play(s,fs)
    # status=sd.wait()

    #3
    normalizacja = s / np.max(np.abs(s))

    #ms
    tc = len(s) / fs * 1000

    #os czasu
    t = np.linspace(0, tc, len(s))

    plt.plot(t, normalizacja, color='lightgreen')
    plt.xlabel('Czas [ms]')
    plt.ylabel('Amplituda[dB]')
    plt.title('Sygnal audio')
    plt.show()

    #4
    print("Moim zdaniem dynamika sygnału jest odpowiednia, \n"
          "Zakres amplitudy jest odpowiednio wykorzystany, jest w zakresie <0,75,-1>\n"
          "Przesterowanie nie występuje, amplituda zarówno na początku jak i na końcu wynosi 0.\n"
          "Szum ma charakter losowy(?), oceniam naocznie, plik audio został wygenerowany przez\n"
          "narzędzie narakeet.com")


#2 ZASTOSOWANIE OKIEN KROCZĄCYCH
def oknaKroczace(ms):
    s, fs = sf.read('student.wav', dtype='float32')
    #potrzebne do wykresu sygnalu
    normalizacja = s / np.max(np.abs(s))
    tc = len(s) / fs * 1000
    t = np.linspace(0, tc, len(s))
    #-------------------------------------
    # ms = 50
    N = int(ms * fs / 1000)

    # Podzial sygnalu na ramki
    ramki = [s[i:i + N] for i in range(0, len(s), N)]

    energia = [np.sum(np.square(ramka)) for ramka in ramki]

    przejscie = [np.sum(np.abs(np.diff(np.sign(ramka)))) / (2 * len(ramka)) for ramka in ramki]

    for i in range(len(energia)):
        print("Ramka", i + 1, "- Energia:", energia[i], ", Przejścia przez zero:", przejscie[i])

    #normalizacja
    max_energia = np.max(energia)
    if max_energia != 0:
        energia = [e / max_energia for e in energia]

    max_przejscie = np.max(przejscie)
    if max_przejscie != 0:
        przejscie = [p / max_przejscie for p in przejscie]

    t_ramki = np.arange(0, len(ramki) * ms, ms)

    plt.plot(t, normalizacja, color='lightgreen', label='sygnal')
    plt.plot(t_ramki, przejscie, color='blue', label='przejscie')
    plt.plot(t_ramki, energia, color='red', label='energia')
    plt.xlabel('Czas [ms]')
    plt.ylabel('Amplituda[dB]')
    plt.title(f"Sygnal audio dla ramek {ms} ms")
    plt.legend()
    # plt.savefig(f"{ms}ms.png")
    plt.show()


# 3 Analiza częstotliwościowa
def analiza():
    #1
    s, fs = sf.read('student.wav', dtype='float32')
    poczatek = 200
    koniec = poczatek + 2048

    okno = s[poczatek:koniec]

    plt.plot(okno, color='lightgreen')
    plt.xlabel('n')
    plt.ylabel('Amplituda[dB]')
    # plt.show()

    #2
    dlugoscOkna = len(okno)
    hamming = np.hamming(dlugoscOkna)
    maska = okno * hamming

    plt.plot(hamming)
    plt.xlabel('n')
    plt.ylabel('Amplituda[dB]')
    plt.title('hamming')
    plt.show()

    plt.plot(maska)
    plt.xlabel('n')
    plt.ylabel('Amplituda[dB]')
    plt.title('zamaskowane okno hamminga')
    plt.show()

    #3
    yf = scipy.fftpack.fft(maska)
    widmo = np.abs(yf)

    plt.plot(widmo)
    plt.title('widmo sygnalu')
    plt.xlabel('hz')
    plt.ylabel('Amplituda[dB]')
    plt.show()

    #4
    skalalog = 20 * np.log10(widmo)
    fs2 = np.fft.fftfreq(len(skalalog), 1 / fs)

    fspolowa = fs2[:len(fs2) // 2]
    widmopolowa = skalalog[:len(fs2) // 2]

    plt.plot(fspolowa, widmopolowa, color='red')
    plt.xlabel('fs')
    plt.ylabel('Amplituda[dB]')
    plt.show()

    #5
    maksimum = np.argmax(widmopolowa[1:]) + 1
    f0 = fspolowa[maksimum]

    plt.plot(fspolowa, widmopolowa, color='red')
    plt.axvline(x=f0, color='blue', linestyle='--')
    plt.xlabel('fs')
    plt.ylabel('Amplituda[dB]')
    plt.title('logarytmiczne widmo')
    plt.show()


#4 ROZPOZNAWANIE SAMOGLOSEK A,E,I,O,U,Y/

def rozpoznawanie():
    s, fs = sf.read('student.wav', dtype='float32')
    poczatek = 200
    koniec = poczatek + 2048

    okno = s[poczatek:koniec]

    p = 20
    a = librosa.lpc(okno, order=p)
    #LPC->liniowe kodowanie predykcyjne
    #filtr lpc modeluje trakt glosowy dla bloku probek sygnalu mowy, niemal wszystkie kodeki parametryczne wykorzystują LPC -> źródło: https://multimed.org/student/amowy/AM_09_kodowanie_mowy.pdf
    # w dużym uproszczeniu LPC to technika wykorzystująca modelowanie sygnału mowy jako kombinację liniową poprzednich próbek, dzięki czemu umożliwia kompresję,analizę i syntezę mowy

    zera = np.pad(a, (0, 2048 - len(a)), mode='constant')
    print("zera", zera)

    widmo = np.log(np.abs(scipy.fftpack.fft(zera))) * -1
    yf = scipy.fftpack.fft(okno)
    widmoLPC = np.log(np.abs(yf))

    max = np.max(widmoLPC[:len(widmo) // 2])
    min = np.min(widmoLPC[:len(widmo) // 2])

    skalowanie = (widmo - np.mean(widmo)) / np.max(widmo)
    skalowanie = skalowanie * (max - min) / 2 + (max + min) / 2
    revSkalowanie = skalowanie + 1

    hz = np.linspace(0, fs / 2, len(widmoLPC) // 2)

    plt.plot(hz, widmoLPC[:len(widmoLPC) // 2], color='blue')
    plt.plot(hz, revSkalowanie[:len(widmoLPC) // 2], color='red')
    plt.xlabel('Hz')
    plt.ylabel('Amplituda[dB]')
    plt.show()
