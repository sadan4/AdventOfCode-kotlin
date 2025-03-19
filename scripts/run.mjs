import {downloadInputFile, parseArgs} from "./common.mjs";
import {execSync} from "node:child_process";
import {existsSync} from "node:fs";

const {year, day, inputFilename} = parseArgs();
let command
switch (process.platform) {
    case "win32":
        command = `gradlew.bat run -DYEAR=${year} -DDAY=${day}`;
        break;
    default:
        command = `./gradlew run -DYEAR=${year} -DDAY=${day}`;

}
if (!existsSync(inputFilename)) {
    await downloadInputFile(year, day, inputFilename);
}
try {
    execSync(command, {
        stdio: "inherit",
    })
} catch (e) {
    process.exit(e?.status ?? 0)
}