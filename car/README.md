# Car module (Android Auto) — MyCar AI

This module adds a minimal Android Auto (Jetpack Car App) launcher that deep-links to the phone-side assistant. It's intended as a safe, minimal entry point for your edge-based in-car assistant.

Quick overview
- Module: `:car` (package `com.zeroclaw.android.car`)
- Car service: `CarService` -> `MyCarSession` -> `HomeScreen`
- Deep link from car UI: `mycar://assistant/open`
- Phone-side activity: `com.zeroclaw.android.assistant.AssistantActivity` handles the deep link and calls the local assistant server at `http://127.0.0.1:5000/assistant`.

How it works
1. User opens the app on the car head unit and taps "Open Assistant".
2. The car module launches an intent with `mycar://assistant/open` which opens `AssistantActivity` on the phone.
3. `AssistantActivity` uses `AssistantManager` (OkHttp + coroutines) to POST JSON to the local server on the phone and displays the short response.

Testing with DHU (Desktop Head Unit)
1. Build the debug APK including the `:car` module:
   ```bash
   ./gradlew assembleDebug
   ```
2. Install the app on your phone:
   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```
3. Ensure the local assistant server is running on the phone and listening on `127.0.0.1:5000` (see example below).
4. Enable Android Auto developer mode on the phone (Android Auto > Settings > tap the version several times).
5. Run the DHU on your desktop and connect to the phone as described in Android for Cars docs:
   https://developer.android.com/training/cars/testing
6. From the DHU car UI: open MyCar AI -> Open Assistant. The phone should open `AssistantActivity` and display the server response.

Example local server (nanohttpd in-app or simple Python for quick desktop testing)

- Quick Python (desktop only; on-phone use Termux or embed nanohttpd/Ktor in the app):
```python
from flask import Flask, request, jsonify
app = Flask(__name__)

@app.route('/assistant', methods=['POST'])
def assistant():
    data = request.get_json() or {}
    query = data.get('query', '')
    # Replace this with your model logic / inference
    return jsonify({'response': f'Received: {query}'})

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000)
```

- On-phone (recommended): use your existing embedded server (nanohttpd is already a dependency) or start a foreground service that launches an HTTP server bound to `127.0.0.1:5000` so `AssistantManager` can reach it reliably.

Notes & next steps
- The car UI is intentionally minimal to reduce driver distraction. For voice-first experiences, consider adding App Actions / Google Assistant integration later.
- Make the on-phone assistant server a foreground service for reliability and to avoid background restrictions.
- Ensure your ProGuard/R8 rules keep OkHttp and coroutines classes if code shrinking is enabled.

If you want, I can:
- Add a small sample nanohttpd-based server implementation inside the `app` module and a foreground service starter.
- Open the PR for `car/android-auto` (the branch is ready) with this README included.
