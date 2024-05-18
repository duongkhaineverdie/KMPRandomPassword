import SwiftUI
import ComposeApp
import Firebase

@main
struct iOSApp: App {
    init() {
        FirebaseApp.configure()
        KoinKt.doInitKoin()
    }

    var body: some Scene {
		WindowGroup {
			ContentView().edgesIgnoringSafeArea(.all)
		}
	}
}
