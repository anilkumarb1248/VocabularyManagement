import { Status } from "./status";

export class IndividualResponse<T> {

    public value: T;
    public status: Status;
    public msg:string;
    public source:string;

    constructor(value:T, msg:string, source:string, status: Status){
        this.value = value;
        this.msg = msg;
        this.source = source;
        this.status = status;
    }

    public getValue(): T {
        return this.value;
    }

    public setValue(value: T): void {
        this.value = value;
    }

    public getStatus(): Status {
        return this.status;
    }

    public setStatus(status: Status): void {
        this.status = status;
    }

    public getMsg(): string {
        return this.msg;
    }

    public setMsg(msg: string): void {
        this.msg = msg;
    }

    public getSource(): string {
        return this.source;
    }

    public setSource(source: string): void {
        this.source = source;
    }
    
}
