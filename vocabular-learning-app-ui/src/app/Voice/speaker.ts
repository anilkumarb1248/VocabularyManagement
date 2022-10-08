import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class Speaker {
	//https://www.bennadel.com/blog/3955-having-fun-with-the-speechsynthesis-api-in-angular-11-0-5.htm


    public sayCommand: string | undefined;
	public rate: number  = 1; // [.25, .5, .75, 1, 1.25, 1.5, 1.75, 2];
	public voice: SpeechSynthesisVoice;

    constructor(){
        let voices = speechSynthesis.getVoices();
		this.voice = (voices[1] || null); // Here indexex will be the voice of speakers
		// this.updateSayCommand("Dummy Text");
    }

    public setVoice(voice:SpeechSynthesisVoice){
		this.voice = voice;
		// this.updateSayCommand("Dummy Text");
    }

    public speak(text: string):void{
        this.stop();
        this.synthesizeSpeechFromText(this.voice, this.rate, text);
    }

	public stopSpeaking():void {
		this.stop();
	}

    // I perform the low-level speech synthesis for the given voice, rate, and text.
	private synthesizeSpeechFromText(voice: SpeechSynthesisVoice,rate: number,text: string): void {
		var utterance = new SpeechSynthesisUtterance(text);
		utterance.voice = this.voice;
		utterance.rate = rate;
		speechSynthesis.speak(utterance);
	}

    public updateSayCommand(text:string): void {
		console.log("it is calling");
		if (!text) {
			return;
		}
		if(!this.voice){
			let voices = speechSynthesis.getVoices();
			this.voice = (voices[1] || null);
		}
		// With the say command, the rate is the number of words-per-minute. As such, we
		// have to finagle the SpeechSynthesis rate into something roughly equivalent for
		// the terminal-based invocation.
		var sanitizedRate = Math.floor(200 * this.rate);
		var sanitizedText = text
			.replace(/[\r\n]/g, " ").replace(/(["'\\\\/])/g, "\\$1");

		this.sayCommand = `say --voice ${this.voice.name} --rate ${sanitizedRate} --output-file=demo.aiff "${sanitizedText}"`;
	}

    // I stop any current speech synthesis.
	public stop(): void {
		if (speechSynthesis.speaking) {
			speechSynthesis.cancel();
		}
	}

    
}
