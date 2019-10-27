// swift-tools-version:5.0
import PackageDescription

let package = Package(
    name: "sdl-parking",
    products: [],
    dependencies: [
        // add your dependencies here, for example:
        // .package(url: "https://github.com/User/Project.git", .upToNextMajor(from: "1.0.0")),
        .package(url: "https://github.com/smartdevicelink/sdl_ios.git", .upToNextMajor(from: "6.2.0")),
    ],
    targets: [
        .target(
            name: "sdl-parking",
            dependencies: [
                // add your dependencies scheme names here, for example:
                // "Project",
                "SmartDeviceLinkSwift",
            ],
            path: "sdl-parking"
        ),
    ]
)
