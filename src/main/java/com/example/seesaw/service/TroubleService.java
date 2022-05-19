package com.example.seesaw.service;

import com.example.seesaw.dto.TroubleAllResponseDto;
import com.example.seesaw.dto.TroubleCommentRequestDto;
import com.example.seesaw.dto.TroubleDetailResponseDto;
import com.example.seesaw.dto.TroubleDto;
import com.example.seesaw.model.*;
import com.example.seesaw.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TroubleService {

    private final TroubleTagRepository troubleTagRepository;
    private final TroubleRepository troubleRepository;
    private final TroubleImageRepository troubleImageRepository;
    private final TroubleS3Service troubleS3Service;
    private final TroubleCommentRepository troubleCommentRepository;
    private final TroubleCommentLikeRepository troubleCommentLikeRepository;

    private final ConvertTimeService convertTimeService;
    private final UserService userService;
    private final UserRepository userRepository;

    //Trouble 글 등록
    public void registerTrouble(TroubleDto troubleDto, List<MultipartFile> files, User user) {
        checkTrouble(troubleDto);
        List<String> imagePaths = new ArrayList<>();

        if(files == null){
            imagePaths.add("https://myseesaw.s3.ap-northeast-2.amazonaws.com/DictBasicCard.svg");
        } else {
            imagePaths.addAll(troubleS3Service.upload(files));
        }
        Trouble trouble = new Trouble(
                troubleDto.getTitle(),
                troubleDto.getContents(),
                troubleDto.getQuestion(),
                troubleDto.getAnswer(),
                0L,
                user
        );
        troubleRepository.save(trouble);

        for (String imagePath : imagePaths) {
            TroubleImage troubleImage = new TroubleImage(imagePath, user, trouble);
            troubleImageRepository.save(troubleImage);
        }

        List<String> tags = troubleDto.getTagNames();
        for (String tag : tags) {
            if(!tag.equals("")){
                TroubleTag troubleTag = new TroubleTag(tag, user, trouble);
                troubleTagRepository.save(troubleTag);
            }
        }
    }

    //고민글 수정 시 정보조회
    public TroubleDto findTrouble(Long troubleId) {
        Trouble trouble = troubleRepository.findById(troubleId).orElseThrow(
                () -> new IllegalArgumentException("고민 Id에 해당하는 글이 없습니다.")
        );


        List<TroubleTag> troubleTags = troubleTagRepository.findAllByTroubleId(troubleId);

        List<String> troubleTagList = new ArrayList<>();
        for(TroubleTag troubleTag : troubleTags){
            troubleTagList.add(troubleTag.getTagName());
        }

        List<TroubleImage> troubleImages = troubleImageRepository.findAllByTroubleId(troubleId);
        if (troubleImages.isEmpty()){
            throw new IllegalArgumentException("고민 Id에 해당하는 이미지가 없습니다.");
        }
        List<String> troubleImageList = new ArrayList<>();
        for(TroubleImage troubleImage : troubleImages){
            troubleImageList.add(troubleImage.getTroubleImage());
        }

        return new TroubleDto(trouble.getTitle(), trouble.getContents(), trouble.getQuestion(), trouble.getAnswer(), troubleTagList, troubleImageList);
    }

    //고민글 수정
    public void updateTrouble(TroubleDto troubleDto, List<MultipartFile> files, Long troubleId, User user) {
        Trouble trouble = troubleRepository.findById(troubleId).orElseThrow(
                () -> new IllegalArgumentException("고민 Id에 해당하는 고민글이 없습니다.")
        );
        //고민글 작성자 검사
        Long troubleUserId = trouble.getUser().getId();
        if(!user.getId().equals(troubleUserId)){
            throw new IllegalArgumentException("작성자가 아니므로 고민글 수정이 불가합니다.");
        }
        checkTrouble(troubleDto);

        trouble.update(troubleDto);

        List<String> imagePaths = new ArrayList<>();
        if(files == null && troubleDto.getTroubleImages().isEmpty()){
            imagePaths.add("https://myseesaw.s3.ap-northeast-2.amazonaws.com/ddddd23sdfasf.jpg");
            troubleS3Service.delete(troubleId, troubleDto.getTroubleImages());
            troubleImageRepository.deleteAllByTroubleId(troubleId);
        } else if(files!=null) {
            imagePaths.addAll(troubleS3Service.update(troubleId, troubleDto.getTroubleImages(), files));
        } else{
            imagePaths = troubleDto.getTroubleImages();
            troubleS3Service.delete(troubleId, troubleDto.getTroubleImages());
            troubleImageRepository.deleteAllByTroubleId(troubleId);
        }

        for (String imagePath : imagePaths) {
            TroubleImage troubleImage = new TroubleImage(imagePath, user, trouble);
            troubleImageRepository.save(troubleImage);
        }
        List<String> tags = troubleDto.getTagNames();
        troubleTagRepository.deleteAllByTroubleId(troubleId);
        for (String tag : tags) {
            if(!tag.equals("")){
                TroubleTag troubleTag = new TroubleTag(tag, user, trouble);
                troubleTagRepository.save(troubleTag);
            }
        }
    }

    //고민글 유효성 검사
    public void checkTrouble(TroubleDto troubleDto) {
        if (troubleDto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목입력은 필수값입니다.");
        } else if (troubleDto.getContents().isEmpty()) {
            throw new IllegalArgumentException("내용입력은 필수값입니다.");
        } else if (troubleDto.getQuestion().isEmpty()) {
            throw new IllegalArgumentException("질문자세대 입력은 필수값입니다.");
        } else if (troubleDto.getAnswer().isEmpty()) {
            throw new IllegalArgumentException("답변자세대 입력은 필수값입니다.");
        }
    }

    // 고민 상세보기
    public TroubleDetailResponseDto findDetailTrouble(Long troubleId, User user) {
        Trouble trouble = troubleRepository.findById(troubleId).orElseThrow(
                () -> new IllegalArgumentException("고민 Id에 해당하는 글이 없습니다.")
        );
        TroubleDto troubleDto = findTrouble(troubleId);

        TroubleDetailResponseDto troubleDetailResponseDto = new TroubleDetailResponseDto(troubleDto);
        troubleDetailResponseDto.setWriter(trouble.getUser().getNickname()); // 작성자 닉네임
        troubleDetailResponseDto.setNickname(user.getNickname()); // 댓글 작성할 본인 닉네임
        troubleDetailResponseDto.setProfileImages(userService.findUserProfiles(trouble.getUser()));
        String postTime = convertTimeService.convertLocaldatetimeToTime(trouble.getCreatedAt());
        troubleDetailResponseDto.setTroubleTime(postTime);
        troubleDetailResponseDto.setViews(trouble.getViews());
        trouble.setViews(trouble.getViews()+1);
        troubleRepository.save(trouble);

        List<TroubleComment> troubleComments = troubleCommentRepository.findAllByTroubleIdOrderByLikeCountDesc(troubleId);
        troubleDetailResponseDto.setCommentCount((long) troubleComments.size());
        List<TroubleCommentRequestDto> troubleCommentRequestDtos = new ArrayList<>();
        for(TroubleComment troubleComment:troubleComments){
            TroubleCommentRequestDto troubleCommentRequestDto = new TroubleCommentRequestDto(troubleComment);
            User commentUser = userRepository.findByNickname(troubleComment.getNickname()).orElseThrow(
                    () -> new IllegalArgumentException("고민댓글에 해당하는 사용자를 찾을 수 없습니다."));
            troubleCommentRequestDto.setProfileImages(userService.findUserProfiles(commentUser));
            troubleCommentRequestDtos.add(troubleCommentRequestDto);
        }
        troubleDetailResponseDto.setTroubleComments(troubleCommentRequestDtos);

        return troubleDetailResponseDto;
    }

    public List<TroubleAllResponseDto> findAllTroubles() {
        List<Trouble> troubles = troubleRepository.findAllByOrderByCreatedAtDesc();
        return getTroubles(troubles);
    }

    public List<TroubleAllResponseDto> findViewTroubles() {
        List<Trouble> troubles = troubleRepository.findAllByOrderByViewsDesc();
        return getTroubles(troubles);
    }

    // 고민글 전체 리스트 조회
    public List<TroubleAllResponseDto> getTroubles(List<Trouble> troubles){
        if(troubles.isEmpty()){
            throw new IllegalArgumentException("작성된 고민글이 없습니다.");
        }
        List<TroubleAllResponseDto> troubleAllResponseDtos = new ArrayList<>();

        for(Trouble trouble:troubles){
            TroubleDto troubleDto = findTrouble(trouble.getId());
            TroubleAllResponseDto troubleAllResponseDto = new TroubleAllResponseDto(troubleDto);
            troubleAllResponseDto.setTroubleId(trouble.getId());
            troubleAllResponseDto.setNickname(trouble.getUser().getNickname());
            troubleAllResponseDto.setProfileImages(userService.findUserProfiles(trouble.getUser()));
            String postTime = convertTimeService.convertLocaldatetimeToTime(trouble.getCreatedAt());
            troubleAllResponseDto.setPostTime(postTime);
            troubleAllResponseDto.setViews(trouble.getViews());
            List<TroubleComment> troubleComments = troubleCommentRepository.findAllByTroubleId(trouble.getId());
            troubleAllResponseDto.setCommentCount((long) troubleComments.size());
            troubleAllResponseDtos.add(troubleAllResponseDto);
        }
        return troubleAllResponseDtos;
    }
    // 댓글 리스폰스용
    public TroubleCommentRequestDto getTroubleCommentDto(User user, TroubleComment troubleComment) {
        TroubleCommentRequestDto troubleCommentRequestDto = new TroubleCommentRequestDto(troubleComment);
        User commentUser = userRepository.findByNickname(troubleComment.getNickname()).orElseThrow(
                () -> new IllegalArgumentException("고민댓글에 해당하는 사용자를 찾을 수 없습니다."));
        troubleCommentRequestDto.setProfileImages(userService.findUserProfiles(commentUser));
        troubleCommentRequestDto.setCommentLikeCount(troubleComment.getLikeCount());
        String troubleCommentTime = convertTimeService.convertLocaldatetimeToTime(troubleComment.getCreatedAt());
        troubleCommentRequestDto.setCommentTime(troubleCommentTime);
        TroubleCommentLike savedTroubleCommentLike = troubleCommentLikeRepository.findByTroubleCommentAndUserId(troubleComment, user.getId());
        troubleCommentRequestDto.setCommentLikeStatus(savedTroubleCommentLike != null);
        return troubleCommentRequestDto;
    }

    // TroubleCommentDto Response 용 메서드
//    private TroubleCommentRequestDto getTroubleCommentDto(User user, TroubleComment troubleComment) {
//        User commentUser = userRepository.findByNickname(troubleComment.getNickname()).orElseThrow(
//                () -> new IllegalArgumentException("고민댓글에 해당하는 사용자를 찾을 수 없습니다."));
//        System.out.println(commentUser);
//        // 댓글 등록할 시 response 해주는 용
//        TroubleCommentRequestDto troubleCommentDto = new TroubleCommentRequestDto(troubleComment);
//        System.out.println(troubleCommentDto);
//        // 고민 댓글 시간
//        String troubleCommentTime = convertTimeService.convertLocaldatetimeToTime(troubleComment.getCreatedAt());
//        troubleCommentDto.setCommentTime(troubleCommentTime);
//        // 프로필 이미지
//        troubleCommentDto.setProfileImages(userService.findUserProfiles(commentUser));
//        // 좋아요 눌렀는지 안눌렀는지 상태
//        TroubleCommentLike savedTroubleCommentLike = troubleCommentLikeRepository.findByTroubleCommentAndUserId(troubleComment, user.getId());
//        troubleCommentDto.setCommentLikeStatus(savedTroubleCommentLike != null);
//        System.out.println(troubleCommentDto);
//        return troubleCommentDto;
//    }
}
