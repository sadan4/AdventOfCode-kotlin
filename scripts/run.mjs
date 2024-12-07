import {parseArgs} from "./common.mjs";
import {exec} from "child_process";
import {writeFileSync} from "node:fs";

const {year, day} = parseArgs();


writeFileSync(1, `-DYEAR=${year} -DDAY=${day}`)