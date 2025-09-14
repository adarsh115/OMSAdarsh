use std::env;
use std::path::PathBuf;

fn main() {
    let out_dir = PathBuf::from("src/model");

    prost_build::Config::new()
        .out_dir(out_dir)
        .compile_protos(
            &["proto/order_placed.proto", "proto/risk_approved.proto"],
            &["proto/"],
        )
        .unwrap();
}