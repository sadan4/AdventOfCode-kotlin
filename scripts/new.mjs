import {copyFile, mkdir, readFile, writeFile} from "node:fs/promises";
import {parseArgs, TEMPLATE_FILE,} from "./common.mjs";
import config from "../.env.json" with {type: "json"};

const {year, day, filename, dir, inputFilename} = parseArgs();

const template = (await readFile(TEMPLATE_FILE, "utf-8")).replaceAll("%YY%", year).replaceAll("%DD%", day).replaceAll("%YYYY%", year.padStart(4, "20"));
console.log(`Making dir: ${dir}`)
await mkdir(dir, {recursive: true});
console.log(`Writing boilerplate to ${filename}`)
await writeFile(filename, template);
const url = `https://adventofcode.com/20${year}/day/${day}/input`;
console.log(`Fetching input from ${url}`)
const input = await (await fetch(new Request(url, {
    headers: {
        Cookie: `session=${config.cookie}`,
        "User-Agent": config.userAgent
    }
}))).text();
console.log(`Preview of input:\n${input.substring(0, 100)}`)
console.log(`Writing input to ${inputFilename}`)
await writeFile(inputFilename, input)


