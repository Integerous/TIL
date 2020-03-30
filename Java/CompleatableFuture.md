## CompletableFuture 사용 예시
>급하게 사용하게 된 CompletableFuture 사용 기록

~~~java
/**
     * 벤더 상세조회 (Async)
     *
     * @param utmCode 벤더 식별 아이디
     * @return 벤더 상세조회 응답 객체
     */
    public DetailedVendorDTO getDetailedVendorAsync(String utmCode) {
        CompletableFuture<List<String>> vendorHasServiceAsync = getVendorHasServiceAsync(utmCode);
        CompletableFuture<List<TokenResponseDTO>> tokensByUtmCodeAsync = getTokensByUtmCodeAsync(utmCode);

        DetailedVendorDTO detailedVendorDTO = vendorHasServiceAsync
                .thenCombine(tokensByUtmCodeAsync,
                        (services, tokens) -> DetailedVendorDTO.builder()
                                .servicesByVendor(services)
                                .tokenList(tokens)
                                .build()
                )
                .join();

        return detailedVendorDTO;
    }

    private CompletableFuture<List<String>> getVendorHasServiceAsync(String utmCode) {
        CompletableFuture future = new CompletableFuture<>();
        new Thread(() -> {
            log.info("새로운 쓰레드로 벤더가 사용중인 서비스 목록 조회");
            List<VendorHasService> vendorHasServiceByUtmCode = vendorHasServiceReadRepository.findVendorHasServiceByUtmCode(utmCode);
            List<String> services = vendorHasServiceByUtmCode.stream()
                    .map(DynamoTable::getSk)
                    .collect(Collectors.toList());
            future.complete(services);
        }).start();

        return future;
    }

    private CompletableFuture<List<TokenResponseDTO>> getTokensByUtmCodeAsync(String utmCode) {
        CompletableFuture future = new CompletableFuture<>();
        new Thread(() -> {
            log.info("새로운 쓰레드로 벤더가 사용중인 토큰 목록 조회");
            List<Token> tokensByUtmCode = tokenReadRepository.findTokensByUtmCode(utmCode);
            List<TokenResponseDTO> tokens = tokensByUtmCode.stream()
                    .map(TokenResponseDTO::from)
                    .collect(Collectors.toList());
            future.complete(tokens);
        }).start();

        return future;
    }
~~~

### Reference
- https://brunch.co.kr/@springboot/267
