export class Verb {

    public verbId: number;
    public baseForm: string;
    public pastTenseForm: string;
    public pastParticipleForm: string;
    public thirdPersonBaseForm: string;
    public progressiveForm: string;
    public phonetics: string;
    public meanings:string[];
    public examples:string[];
    public baseFormExamples:string[];
    public pastTenseExample:string[];
    public pastParticipleFormExample:string[];
    public createdTimeStamp: Date;
    public updatedTimeStamp: Date;

    constructor(verbId:number, baseForm:string, pastTenseForm:string, pastParticipleForm:string, thirdPersonBaseForm:string,progressiveForm:string,phonetics:string,meanings:string[],examples:string[], baseFormExamples:string[],pastTenseExample:string[],pastParticipleFormExample:string[], createdTimeStamp:Date, updatedTimeStamp:Date) {
        this.verbId = verbId;
        this.baseForm = baseForm;
        this.pastTenseForm = pastTenseForm;
        this.pastParticipleForm = pastParticipleForm;
        this.thirdPersonBaseForm = thirdPersonBaseForm;
        this.progressiveForm = progressiveForm;
        this.phonetics = phonetics;
        this.meanings = meanings;
        this.examples = examples;
        this.baseFormExamples = baseFormExamples;
        this.pastTenseExample = pastTenseExample;
        this.pastParticipleFormExample = pastParticipleFormExample;
        this.createdTimeStamp = createdTimeStamp;
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public getVerbId(): number {
        return this.verbId;
    }

    public setVerbId(verbId: number): void {
        this.verbId = verbId;
    }

    public getBaseForm(): string {
        return this.baseForm;
    }

    public setBaseForm(baseForm: string): void {
        this.baseForm = baseForm;
    }

    public getPastTenseForm(): string {
        return this.pastTenseForm;
    }

    public setPastTenseForm(pastTenseForm: string): void {
        this.pastTenseForm = pastTenseForm;
    }

    public getPastParticipleForm(): string {
        return this.pastParticipleForm;
    }

    public setPastParticipleForm(pastParticipleForm: string): void {
        this.pastParticipleForm = pastParticipleForm;
    }

    public getThirdPersonBaseForm(): string {
        return this.thirdPersonBaseForm;
    }

    public setThirdPersonBaseForm(thirdPersonBaseForm: string): void {
        this.thirdPersonBaseForm = thirdPersonBaseForm;
    }

    public getProgressiveForm(): string {
        return this.progressiveForm;
    }

    public setProgressiveForm(progressiveForm: string): void {
        this.progressiveForm = progressiveForm;
    }

    public getPhonetics(): string {
        return this.phonetics;
    }

    public setPhonetics(phonetics: string): void {
        this.phonetics = phonetics;
    }

    public getMeanings(): string[] {
        return this.meanings;
    }

    public setMeanings(meanings: string[]): void {
        this.meanings = meanings;
    }

    public getExamples(): string[] {
        return this.examples;
    }

    public setExamples(examples: string[]): void {
        this.examples = examples;
    }

    public getBaseFormExamples(): string[] {
        return this.baseFormExamples;
    }

    public setBaseFormExamples(baseFormExamples: string[]): void {
        this.baseFormExamples = baseFormExamples;
    }

    public getPastTenseExample(): string[] {
        return this.pastTenseExample;
    }

    public setPastTenseExample(pastTenseExample: string[]): void {
        this.pastTenseExample = pastTenseExample;
    }

    public getPastParticipleFormExample(): string[] {
        return this.pastParticipleFormExample;
    }

    public setPastParticipleFormExample(pastParticipleFormExample: string[]): void {
        this.pastParticipleFormExample = pastParticipleFormExample;
    }

    public getCreatedTimeStamp(): Date {
        return this.createdTimeStamp;
    }

    public setCreatedTimeStamp(createdTimeStamp: Date): void {
        this.createdTimeStamp = createdTimeStamp;
    }

    public getUpdatedTimeStamp(): Date {
        return this.updatedTimeStamp;
    }

    public setUpdatedTimeStamp(updatedTimeStamp: Date): void {
        this.updatedTimeStamp = updatedTimeStamp;
    }
    
}
