import { readdir, stat } from "node:fs/promises";
import { getCodeFilename, getInputFilename, getYearDir, parseArgs } from "./common.mjs";
import { spawn } from "node:child_process";
import { createWriteStream } from "node:fs";
import { join } from "node:path";
import { Duplex, PassThrough, Readable, Writable } from "node:stream";
import assert from "node:assert";
import { Socket } from "node:net";

/**
 * @param {string} year
 * @param {string} day
 * @returns {Promise<boolean>}
 */
async function hasSolution(year, day) {
    try {
        return !(await stat(getCodeFilename(year, day))).isDirectory()
    } catch (e) {
        if (e?.code === "ENOENT") return false;
        throw e;
    }
}

/**
 * @param {string} year 
 * @returns {Promise<string[]>}
 */
async function genDays(year) {
    const hasDay = hasSolution.bind(null, year);
    const dayNumbers = (await readdir(getYearDir(year))).map(day => {
        const dayNameRegex = /\d+$/;
        const res = day.match(dayNameRegex)?.[0];
        if (res) return res;
        throw new Error(`day folder name: ${day} did not match pattern: ${dayNameRegex}`);
    });
    const hasDayMap = await Promise.all(dayNumbers.map(hasDay));
    return dayNumbers.filter((_, i) => hasDayMap[i])
}
/**
 * @param {string} year
 * @param {string[]} days 
 */
async function results(year, days) {
    assert(days.length !== 0, "Days must have at least one element")
    const todo = days;
    let cur;
    while (cur = days.pop()) {
        Writable
        const outstream = new PassThrough();
        // https://stackoverflow.com/questions/78963790/the-argument-stdio-is-invalid-received-customwritablestream
        const child = spawn("node", [
            join(import.meta.dirname, "run.mjs"),
            `--year=${year}`,
            `--day=${cur}`
        ], {
            stdio: ["ignore", "pipe", "pipe"],
        })
        child.stdout.pipe(outstream);
        child.stderr.pipe(outstream);
        /**
         * @type {number}
         */
        const code = await new Promise(res => child.on("exit", res));
        if (code !== 0) {
            console.error(`day ${cur} failed`);
            outstream.pipe(process.stdout);
            return;
        } else {
            console.log(`day ${cur} passed`)
        }
    }
    console.log("All days passed")
}
async function main() {
    const { year, day } = parseArgs(false);
    if (!year) {
        throw new Error("No year passed, please pass a year to check")
    }
    const days = day ? [day] : await genDays(year);
    await results(year, days);
}

main()
