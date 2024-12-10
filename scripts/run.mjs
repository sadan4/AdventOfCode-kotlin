import {parseArgs} from "./common.mjs";
import {exec} from "node:child_process";
import {readdirSync, writeFileSync} from "node:fs";
import {execFileSync, execSync} from "node:child_process";

const {year, day} = parseArgs();
let command
switch (process.platform) {
    case "win32":
        command = `gradlew.bat run -DYEAR=${year} -DDAY=${day}`;
        break;
    default:
        command = `./gradlew run -DYEAR=${year} -DDAY=${day}`;

}
try {
    execSync(command, {
        stdio: "inherit",
    })
} catch (e) {
    process.exit(e?.status ?? 0)
}