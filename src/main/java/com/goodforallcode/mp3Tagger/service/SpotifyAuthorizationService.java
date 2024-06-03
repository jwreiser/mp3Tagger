package com.goodforallcode.mp3Tagger.service;

public class SpotifyAuthorizationService {
/*
    private static final String redirectUri = "http://localhost:8888/callback";

    public static void login() {
        String state = generateRandomString(16);

        String scope = "user-read-private user-read-email";
        String authorizeUrl = String.format("https://accounts.spotify.com/authorize?response_type=code&client_id=%s&scope=%s&state=%s",
                clientId, scope, state);

        return new ModelAndView(new RedirectView(authorizeUrl));
    }

    @RequestMapping("/callback")
    public ModelAndView callback(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "state") String state,
                                 HttpServletResponse response) {
        String storedState = Arrays.stream(request.getCookies())
                .filter(cookie -> stateKey.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (state == null || !state.equals(storedState)) {
            return new ModelAndView(new RedirectView("/error?error=state_mismatch"));
        } else {
            response.addCookie(new Cookie(stateKey, ""));
            String accessToken = getAccessToken(code);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            return new ModelAndView(new RedirectView("/#access_token=" + accessToken));
        }
    }

    @RequestMapping("/refresh_token")
    public Map<String, String> refreshToken(@RequestParam(name = "refresh_token") String refreshToken) {
        String tokenUrl = "https://accounts.spotify.com/api/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        Map<String, String> response = new RestTemplate().exchange(tokenUrl, HttpMethod.POST, request, Map.class).getBody();
        return response;
    }

    private String getAccessToken(String code) {
        String tokenUrl = "https://accounts.spotify.com/api/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        Map<String, String> response = new RestTemplate().exchange(tokenUrl, HttpMethod.POST, request, Map.class).getBody();
        return response.get("access_token");
    }

    public static String generateRandomString(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

 */
}
