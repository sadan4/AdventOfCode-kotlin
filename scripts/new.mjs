import {copyFile, mkdir, readFile, writeFile} from "node:fs/promises";
import {downloadInputFile, parseArgs, TEMPLATE_FILE,} from "./common.mjs";
import config from "../.env.json" with {type: "json"};

const {year, day, filename, dir, inputFilename} = parseArgs();

const template = (await readFile(TEMPLATE_FILE, "utf-8")).replaceAll("%YY%", year).replaceAll("%DD%", day).replaceAll("%YYYY%", year.padStart(4, "20"));
console.log(`Making dir: ${dir}`)
await mkdir(dir, {recursive: true});
console.log(`Writing boilerplate to ${filename}`)
await writeFile(filename, template);
await downloadInputFile(year, day, inputFilename);


