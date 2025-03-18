import {parseArgs as node_parseArgs} from "node:util";
import {join, dirname} from "node:path";
export const TEMPLATE_FILE = "./scripts/template"
const BASE_PATH = "./src/main/kotlin/solutions"

/**
 *
 * @param year {string}
 * @param day {string}
 * @returns {string}
 */
export function getCodeFilename(year, day) {
    return join(getYearDir(year),`d${day}/Code.kt`)
}
/**
 * @param {string} year
 * @param {string} day
 */
export function getInputFilename(year, day) {
    return join(getYearDir(year),`d${day}/input.txt`)
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