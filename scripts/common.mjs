import {parseArgs as node_parseArgs} from "node:util";
import {join, dirname} from "node:path";
import config from "../.env.json" with {type: "json"};
import {writeFile} from "node:fs/promises";

export const TEMPLATE_FILE = "./scripts/template"
const BASE_PATH = "./src/main/kotlin/solutions"

/**
 * @param {string} year
 * @param {string} day
 * @param {string} inputFilename
 */
export async function downloadInputFile(year, day, inputFilename) {
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
    if(input.startsWith("Puzzle inputs differ by user")) {
        throw new Error("Invalid auth token");
    }
    await writeFile(inputFilename, input)
}

/**
 *
 * @param year {string}
 * @param day {string}
 * @returns {string}
 */
export function getCodeFilename(year, day) {
    return join(getYearDir(year), `d${day}/Code.kt`)
}

/**
 * @param {string} year
 * @param {string} day
 */
export function getInputFilename(year, day) {
    return join(getYearDir(year), `d${day}/input.txt`)
}

/**
 *
 * @param year {string}
 * @param day {string}
 * @returns {string}
 */
export function getTodaysDir(year, day) {
    return dirname(getCodeFilename(year, day))
}

/**
 *
 * @param {string} year
 * @returns {string}
 */
export function getYearDir(year) {
    return join(BASE_PATH, `y${year}`)
}

function getToday() {
    const d = new Date();

    return {
        year: d.getFullYear().toString().substring(2),
        day: (d.getDate()).toString()
    }
}

/**
 * @typedef {Record<"year"|"day"|"filename"|"dir"|"inputFilename", string>} opts
 */
/**
 * @overload
 * @param {true} [useDefault=true]
 * @returns {opts}
 */

/**
 * @overload
 * @param {false} [useDefault=true]
 * @returns {Pick<opts, "year" | "day">}
 */
/**
 *
 * @returns {Pick<opts, "day" | "year"> | Partial<Omit<opts, "day" | "year">>}
 */
export function parseArgs(useDefault = true) {
    /**
     * @satisfies {import("util").ParseArgsConfig["options"]}
     */
    const options = {
        year: {
            type: "string",
            default: useDefault ? getToday().year : "",
            short: "y"
        },
        day: {
            type: "string",
            default: useDefault ? getToday().day : "",
            short: "d"
        }
    }
    const {year, day} = node_parseArgs({args: process.argv.slice(2), options}).values;
    return {
        year,
        day,
        ...(
            useDefault && {
                filename: getCodeFilename(year, day),
                dir: getTodaysDir(year, day),
                inputFilename: getInputFilename(year, day)
            }
        )
    }
}